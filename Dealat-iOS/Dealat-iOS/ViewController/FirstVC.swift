//
//  FirstVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 7/22/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import SDWebImage
import SwiftyJSON
import Alamofire

class FirstVC: UIViewController {
    
    @IBOutlet weak var vv : UIView!
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var imgCache : UIImageView!
    var timer = Timer.init()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getUrlConnection()
//        AppDelegate.setupViews()
        
    }
    
    func getUrlConnection(){
//        self.showLoading()
        
        let url = URL(string: "http://deal-at.com/index.php/api/users_control/get_urls/format/json")!
        
        Alamofire.request(url, method: .get, parameters: nil, encoding : URLEncoding(destination: .queryString), headers: Communication.shared.getHearders()).responseObject { (response : DataResponse<CustomResponse>) in
            
            Communication.shared.output(response)
//            self.hideLoading()
            
            switch response.result{
            case .success(let value):
                
                if value.status{
                    
                    let site_url = value.data["site_url"].stringValue
                    let logo_url = value.data["logo_url"].stringValue
                    let currency_ar = value.data["currency_ar"].stringValue
                    let currency_en = value.data["currency_en"].stringValue
                    
                    Provider.shared.currency_ar = currency_ar
                    Provider.shared.currency_en = currency_en
                    
                    if User.isRegistered(){
                        Communication.shared.get_my_info { (res) in
                        }
                    }
                    
                    self.setDefault(site_url, img : logo_url)
                    
                }else{                    
                    self.showAlertError(title: "ConnectionError".localized, message : value.message)
                }
                
            case .failure(let error):
                self.showAlertError(title: "ConnectionError".localized, message : error.localizedDescription)
            }
        }
    }
    
    
    func showAlertError( title : String, message : String){
        
        let alert = UIAlertController.init(title: title, message: message, preferredStyle: UIAlertControllerStyle.alert)
        
        alert.addAction(UIAlertAction.init(title: "TryAgain".localized, style: UIAlertActionStyle.default, handler: { (ac) in
            self.getUrlConnection()
        }))
        
        self.present(alert, animated: true, completion: nil)
    }
    
    
    func setDefault(_ urlString : String, img : String! = nil) {
        
        Communication.shared.baseImgsURL = "\(urlString)/"
        Communication.shared.baseURL = "\(urlString)/" + "index.php/api"
        
        //TODO IMPORTNANT
//        Communication.shared.baseURL = "http://192.168.0.125/Dealat/index.php/api"
//        Communication.shared.baseImgsURL = "http://192.168.9.96/Dealat/"

        
        if let imgString = img,let url = URL.init(string: imgString){
            
            imgCache.sd_setImage(with: url, placeholderImage: nil, options: SDWebImageOptions.refreshCached, progress: nil) { (im, err, type, ur) in
                
                if err != nil{
                    self.img.image = Provider.logoImage
                }else{
                    if let newImg = im{
                        Provider.logoImage = newImg
                        self.img.image = newImg
                    }
                }
                
                
                self.goToViews()
            }
        }else{
            self.img.image = Provider.logoImage

            self.goToViews()
        }
        
        
    }
    
    
    func goToViews(){
        self.vv.isHidden = false

        DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
            AppDelegate.setupViews()
        }
    }
    
}
