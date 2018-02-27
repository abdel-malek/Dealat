//
//  CommericalCell.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import Alamofire

class CommericalCell: UICollectionViewCell {
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var lbl : UILabel!
    
    var newAddVC : NewAddVC!
    
    
    var commercial : Commercial!{
        didSet{
            Provider.sd_setImage(self.img, urlString: commercial.image)
        }
    }
    
    var imageName : String!{
        didSet{
            self.img.image = UIImage.init(named: imageName)
        }
    }
    
    
    var im : IMG! {
        didSet{
            Provider.sd_setImage(self.img, urlString: im.image)
        }
    }
    
    var newAdd : Bool!{
        didSet{
            self.lbl.text = "\(self.tag + 1)"
            //            self.img.image = #imageLiteral(resourceName: "clothes")
        }
    }
    
    var imageNew : (index : Int, imag : UIImage?)!{
        didSet{
            self.lbl.text = "\(imageNew!.index + 1)"
            self.img.image = imageNew!.imag
            
            if imageNew!.index < newAddVC.imagesPaths.count{
                if newAddVC.imagesPaths[imageNew!.index].isEmpty{
                    if let ii = imageNew!.imag{
                        self.uploadImage(ii)
                    }
                    else{
                        self.img.image = imageNew!.imag
                    }
                }
            }else{
                self.img.image = imageNew!.imag
            }
            
        }
    }
    
    
    func uploadImage(_ img : UIImage){
        
        let path = savePhotoLocal(img)
        
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                
                if let p = path{
                    multipartFormData.append(p, withName: "image")
                }else{
                    multipartFormData.append("".getData, withName: "image")
                }
                
        },
            to: "\(Communication.shared.baseURL)/ads_control/ad_images_upload/format/json" ,headers : Communication.shared.getHearders(),
            encodingCompletion: { encodingResult in
                
                switch encodingResult {
                case .success(let upload, _, _):
                    
                    upload.uploadProgress(closure: { (Progress) in
                        print("Upload Progress: \(Progress.fractionCompleted)")
                        if Progress.fractionCompleted == 1{
                        }
                    })
                    
                    upload.responseObject { (response : DataResponse<CustomResponse>) in
                        debugPrint(response)
                        
                        Communication.shared.output(response)
                        //                        self.hideLoading()
                        
                        switch response.result{
                        case .success(let value):
                            
                            if value.status{
                                
                                self.newAddVC.imagesPaths[self.imageNew!.index] = value.data.stringValue
                                
                                Provider.sd_setImage(self.img, urlString: value.data.stringValue)
//                                self.img.image = #imageLiteral(resourceName: "ad6")
                                
                            }else{
                                notific.post(name:_RequestErrorNotificationReceived.not, object: value.message)
                            }
                            break
                        case .failure(let error):
                            print(error.localizedDescription)
                            notific.post(name: _ConnectionErrorNotification.not, object: error.localizedDescription)
                            break
                        }
                        
                    }
                case .failure(let encodingError):
                    //                    self.hideLoading()
                    print(encodingError)
                }
        })
        
        
    }
    
    func savePhotoLocal(_ img : UIImage) -> URL?{
        let tt = img.resized(toWidth: 512)
        
        var imageData = UIImagePNGRepresentation(tt!)
        
        let imageSize: Int = imageData!.count
        print("size of image in KB: %f ", imageSize / 1024)
        
        if (imageSize / 1024) > 2000 {
            
            return nil
        }
        let random = CGFloat.random() + CGFloat(self.imageNew!.index)
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            
            let path = dir.appendingPathComponent("img\(random).png")
            
            //writing
            do {
                try imageData!.write(to: path, options: Data.WritingOptions.atomic)
            }
            catch {}
        }
        
        let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        
        return  rrr.appendingPathComponent("img\(random).png")
    }
    
    
    
}
