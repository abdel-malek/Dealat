//
//  NewAddVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/13/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import Photos
import YangMingShan
import DropDown
import Alamofire

class NewAddVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout, UITextViewDelegate,YMSPhotoPickerViewControllerDelegate {
    
    @IBOutlet weak var collectionView : UICollectionView!
    
    let cellIdentifier = "imageCellIdentifier"
    var images: NSArray! = []
    
    @IBOutlet weak var locationLbl : UITextField!
    @IBOutlet weak var categoryLbl : UITextField!
    
    
    var locationDropDown = DropDown()
    var locations = [Location]()
    
    var selectedLocation : Location!
    
    var selectedCategory : Cat!{
        didSet{
            if self.categoryLbl != nil{
                self.categoryLbl.text = selectedCategory.category_name
            }
        }
    }
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        getData()
        setupViews()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_data_lists { (locations, types, educations, schedules) in
            self.hideLoading()
            
            self.locations = locations
            self.setupLocations()
        }
    }
    
    func setupViews(){
        self.collectionView.delegate = self
        self.collectionView.dataSource = self
    }
    
    
    func setupLocations(){
        var array = [String]()
        
        for i in self.locations{
            array.append(i.city_name + " - " + i.location_name)
        }
        
        locationDropDown.dataSource = array
        locationDropDown.anchorView = self.locationLbl
        locationDropDown.direction = .bottom
        locationDropDown.bottomOffset = CGPoint(x: 0, y: locationLbl.bounds.height)
        
        locationDropDown.selectionAction = { [unowned self] (index, item) in
            self.locationLbl.text = item
            self.selectedLocation = self.locations[index]
        }
    }
    
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        switch indexPath.section {
        case 0:
            return UITableViewAutomaticDimension
        default:
            return 50
        }
    }
    
    func textView(_ textView: UITextView, shouldChangeTextIn range: NSRange, replacementText text: String) -> Bool {
        
        
        let size = textView.bounds.size
        let newSize = textView.sizeThatFits(CGSize(width: size.width,
                                                   height: CGFloat.maximum(size.width, size.height)))
        
        // Resize the cell only when cell's size is changed
        if size.height != newSize.height {
            UIView.setAnimationsEnabled(false)
            tableView?.beginUpdates()
            tableView?.endUpdates()
            UIView.setAnimationsEnabled(true)
            
            let thisIndexPath = IndexPath.init(row: 2, section: 1)
            
            tableView.scrollToRow(at: thisIndexPath, at: .bottom, animated: false)
        }
        
        return true
    }
    
    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        if indexPath.section == 1,indexPath.row == 1{
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ChooseCatVC2") as! ChooseCatVC
            
            let c = Cat()
            c.category_name = "ChooseCategory".localized
            c.children = Provider.shared.cats
            
            vc.cat = c
            vc.newAdd = self
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            self.present(vc, animated: true, completion: nil)
        }
        if indexPath.section == 1,indexPath.row == 2{
            self.locationDropDown.show()
        }
    }
    
    
}

//MARK: CollectionView
extension NewAddVC{
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 8
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        cell.tag = indexPath.row
        cell.newAdd = true
        
        if images != nil && indexPath.row < images.count {
            cell.img.image = self.images.object(at: (indexPath as NSIndexPath).item) as? UIImage
        }else{
            cell.img.image = nil
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let w = collectionView.frame.width - 12
        return CGSize(width: w / 4, height: w / 4)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        print(indexPath.row + 1)
        
        if indexPath.row >= self.images.count{
            let pickerViewController = YMSPhotoPickerViewController.init()
            
            var cnt : Int = 8
            cnt -= (self.images != nil) ? self.images.count  : 0
            pickerViewController.numberOfPhotoToSelect = UInt(cnt)
            
            pickerViewController.theme.titleLabelTextColor = Theme.Color.White
            pickerViewController.theme.navigationBarBackgroundColor = Theme.Color.red
            pickerViewController.theme.tintColor = Theme.Color.White
            pickerViewController.theme.orderTintColor = Theme.Color.red
            pickerViewController.theme.orderLabelTextColor = Theme.Color.White
            pickerViewController.theme.cameraVeilColor = Theme.Color.red
            pickerViewController.theme.cameraIconColor = UIColor.white
            pickerViewController.theme.statusBarStyle = .lightContent
            
            self.yms_presentCustomAlbumPhotoView(pickerViewController, delegate: self)
        }else{
            let alert = UIAlertController.init(title: "Would you like to:", message: "", preferredStyle: .actionSheet)
            
            
            alert.addAction(UIAlertAction.init(title: "Make as Main", style: .default, handler: { (ac) in
                
                let mutableImages: NSMutableArray! = NSMutableArray.init(array: self.images)
                let temp = mutableImages.object(at: indexPath.row)
                
                mutableImages.removeObject(at: indexPath.row)
                mutableImages.insert(temp, at: 0)
                
                self.images = NSArray.init(array: mutableImages)
                self.collectionView.reloadData()
            }))
            
            
            alert.addAction(UIAlertAction.init(title: "Delete Photo", style: .destructive, handler: { (ac) in
                
                self.deletePhotoImage(indexPath.row)
                
                self.collectionView.reloadData()
            }))
            
            
            
            alert.addAction(UIAlertAction.init(title: "Cancel".localized, style: .cancel, handler: nil))
            
            self.present(alert, animated: true, completion: nil)
            
        }
    }
}


//MARK: Photos YangMingShan
extension NewAddVC{
    
    @objc func deletePhotoImage(_ index: Int) {
        let mutableImages: NSMutableArray! = NSMutableArray.init(array: images)
        mutableImages.removeObject(at: index)
        
        self.images = NSArray.init(array: mutableImages)
        self.collectionView.reloadData()
    }
    
    // MARK: - YMSPhotoPickerViewControllerDelegate
    
    func photoPickerViewControllerDidReceivePhotoAlbumAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow photo album access?", message: "Need your permission to access photo albumbs", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        self.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewControllerDidReceiveCameraAccessDenied(_ picker: YMSPhotoPickerViewController!) {
        let alertController = UIAlertController.init(title: "Allow camera album access?", message: "Need your permission to take a photo", preferredStyle: .alert)
        let dismissAction = UIAlertAction.init(title: "Cancel", style: .cancel, handler: nil)
        let settingsAction = UIAlertAction.init(title: "Settings", style: .default) { (action) in
            UIApplication.shared.openURL(URL.init(string: UIApplicationOpenSettingsURLString)!)
        }
        alertController.addAction(dismissAction)
        alertController.addAction(settingsAction)
        
        // The access denied of camera is always happened on picker, present alert on it to follow the view hierarchy
        picker.present(alertController, animated: true, completion: nil)
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPicking image: UIImage!) {
        picker.dismiss(animated: true) {
            self.images = [image]
            self.collectionView.reloadData()
        }
    }
    
    func photoPickerViewController(_ picker: YMSPhotoPickerViewController!, didFinishPickingImages photoAssets: [PHAsset]!) {
        
        picker.dismiss(animated: true) {
            let imageManager = PHImageManager.init()
            let options = PHImageRequestOptions.init()
            options.deliveryMode = .highQualityFormat
            options.resizeMode = .exact
            options.isSynchronous = true
            
            let mutableImages: NSMutableArray! = []
            
            let w = self.collectionView.frame.width - 12
            let customSize = CGSize(width: w / 4, height: w / 4)
            
            for i in self.images{
                mutableImages.add(i)
            }
            
            for asset: PHAsset in photoAssets
            {
                imageManager.requestImage(for: asset, targetSize: customSize, contentMode: .aspectFill, options: options, resultHandler: { (image, info) in
                    mutableImages.add(image!)
                })
            }
            
            
            self.images = mutableImages.copy() as? NSArray
            self.collectionView.reloadData()
        }
    }
    
    
    /*func uploadImage(_ img : UIImage){

        let path = savePhotoLocal(img)
        
        Alamofire.upload(
            multipartFormData: { multipartFormData in
                
                if let p = path{
                    multipartFormData.append(p, withName: "image")
                }else{
                    multipartFormData.append("".getData, withName: "image")
                }
                
        },
            to: "http://192.168.9.53/Dealat/index.php/api/ads_control/ad_images_upload/format/json" ,headers : Communication.shared.getHearders(),
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
                        self.hideLoading()
                        Communication.shared.output(response)
                        
                        switch response.result{
                        case .success(let value):
                            
                            if value.status{
                                
                                
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
                    self.hideLoading()
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
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            
            let path = dir.appendingPathComponent("img.png")
            
            //writing
            do {
                try imageData!.write(to: path, options: Data.WritingOptions.atomic)
            }
            catch {}
        }
        
        let rrr = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first!
        
        return  rrr.appendingPathComponent("img.png")
    }
*/
    
}
