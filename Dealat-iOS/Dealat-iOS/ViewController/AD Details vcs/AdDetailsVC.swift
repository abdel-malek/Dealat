//
//  AdDetailsVC.swift
//  Dealat-iOS
//
//  Created by Yahya Tabba on 2/7/18.
//  Copyright © 2018 Tradinos UG. All rights reserved.
//

import UIKit
import AFDateHelper
import SwiftyJSON
import Lightbox
import Photos


class AdDetailsVC: BaseTVC, UICollectionViewDelegate,UICollectionViewDataSource,UICollectionViewDelegateFlowLayout {
    
    @IBOutlet weak var collectionView : UICollectionView!
    @IBOutlet weak var collectionView2 : UICollectionView!
    @IBOutlet weak var collectionViewHeight : NSLayoutConstraint!
    var parentBase: AdDetailsBaseVC?
    var imagesController = LightboxController.init()

    
    //General
    @IBOutlet weak var adStatusLbl : UILabel!
    @IBOutlet weak var adCreatedDatebl : UILabel!
    
    @IBOutlet weak var adExpiresDatebl : UILabel!
    @IBOutlet weak var adExpiresStack : UIStackView!


    @IBOutlet weak var adRejectionLbl : UILabel!
    @IBOutlet weak var adRejectionStack : UIStackView!
    
    @IBOutlet weak var img : UIImageView!
    @IBOutlet weak var vvPrice : UIView!
    @IBOutlet weak var numberLbl : UILabel!
    @IBOutlet weak var nameLbl : UILabel!
    @IBOutlet weak var priceLbl : UILabel!
//    @IBOutlet weak var viewsLbl : UILabel!
    @IBOutlet weak var dateLbl : UILabel!
    @IBOutlet weak var catLbl : UILabel!
//    @IBOutlet weak var sellerLbl : UILabel!
    @IBOutlet weak var negotiableLbl : UILabel!
    @IBOutlet weak var cityLbl : UILabel!
    @IBOutlet weak var locationLbl : UILabel!
    @IBOutlet weak var desLbl : UILabel!
    
    @IBOutlet weak var favVV : UIView!
    @IBOutlet weak var imgFav : UIImageView!


    // 1 Vehicle
    @IBOutlet weak var manufacture_dateLbl : UILabel!
    @IBOutlet weak var is_automaticLbl : UILabel!
    @IBOutlet weak var is_new1 : UILabel!
    @IBOutlet weak var kilometerLbl : UILabel!
    @IBOutlet weak var type_nameLbl : UILabel!
    @IBOutlet weak var type_model_nameLbl : UILabel!
    @IBOutlet weak var engine_capacityLbl : UILabel!

    
    // 2 Property
    @IBOutlet weak var stateLbl : UILabel!
    @IBOutlet weak var rooms_numLbl : UILabel!
    @IBOutlet weak var floors_numberLbl : UILabel!
    @IBOutlet weak var floorLbl : UILabel!
    @IBOutlet weak var with_furnitureLbl : UILabel!
    @IBOutlet weak var spaceLbl : UILabel!
    
    
    // 3 Mobile
    @IBOutlet weak var is_new3 : UILabel!
    @IBOutlet weak var type_nameLbl3 : UILabel!
    
    // 4 Electronic
    @IBOutlet weak var is_new4 : UILabel!
    @IBOutlet weak var type_nameLbl4 : UILabel!
    @IBOutlet weak var sizeLbl : UILabel!

    
    // 5 Fashion
    @IBOutlet weak var is_new5 : UILabel!
    
    // 6 Kids
    @IBOutlet weak var is_new2 : UILabel!
    
    // 7 Sport
    @IBOutlet weak var is_new7 : UILabel!
    
    // 8 Job
    @IBOutlet weak var education_nameLbl : UILabel!
    @IBOutlet weak var certificate_nameLbl : UILabel!
    @IBOutlet weak var schedule_nameLbl : UILabel!
    @IBOutlet weak var genderLbl : UILabel!
    @IBOutlet weak var experienceLbl : UILabel!
    @IBOutlet weak var salaryLbl : UILabel!

    
    // 9 Industry
    @IBOutlet weak var is_new9 : UILabel!
    
    var selectedIndex : Int = 0
    var ad : AD!


    func ifHidden(index : IndexPath) -> Bool{
        
        if let cat = Provider.shared.catsFull.filter({$0.category_id.intValue == self.ad.category_id.intValue}).first,cat.hidden_fields != nil {
            
            switch (index.section,index.row){
                
            case (1,0): return cat.hidden_fields.contains("type_name")
            case (1,1): return cat.hidden_fields.contains("type_model_name")
            case (1,2): return cat.hidden_fields.contains("manufacture_date")
            case (1,3): return cat.hidden_fields.contains("is_automatic")
            case (1,4): return cat.hidden_fields.contains("is_new")
            case (1,5): return cat.hidden_fields.contains("kilometer")
            case (1,6): return cat.hidden_fields.contains("engine_capacity")

            case (2,0): return cat.hidden_fields.contains("space")
            case (2,1): return cat.hidden_fields.contains("rooms_num")
            case (2,2): return cat.hidden_fields.contains("floors_number")
            case (2,3): return cat.hidden_fields.contains("floor")
            case (2,4): return cat.hidden_fields.contains("state")
            case (2,5): return cat.hidden_fields.contains("furniture")
                
            case (3,0): return cat.hidden_fields.contains("type_name")
            case (3,1): return cat.hidden_fields.contains("state")
                
            case (4,0): return cat.hidden_fields.contains("type_name")
            case (4,1): return cat.hidden_fields.contains("state")
            case (4,2): return cat.hidden_fields.contains("size")
                
            case (5,0): return cat.hidden_fields.contains("state")
            case (6,0): return cat.hidden_fields.contains("state")
            case (7,0): return cat.hidden_fields.contains("state")
                
            case (8,0): return cat.hidden_fields.contains("education")
            case (8,1): return cat.hidden_fields.contains("certificate_name")
            case (8,2): return cat.hidden_fields.contains("schedule")
            case (8,3): return cat.hidden_fields.contains("gender")
            case (8,4): return cat.hidden_fields.contains("experience")
            case (8,5): return cat.hidden_fields.contains("salary")
                
                
            case (9,0): return cat.hidden_fields.contains("state")
                
            default:
                break
            }
        }
        
        return false
    }

    
    override func viewDidLoad() {
        
        refreshData()
        getData()
    }
    
    
    override func getRefreshing() {
        Communication.shared.get_ad_details(ad_id: self.ad.ad_id.intValue, template_id: self.ad.tamplate_id.intValue) { (res) in
            self.hideLoading()

            let expairy_date = self.ad.expiry_date
            
            self.ad = res

            if res.expiry_date == nil || res.expiry_date.isEmpty{
                self.ad.expiry_date = expairy_date
            }
            
            self.parentBase?.ad = self.ad
            self.parentBase?.adBase = AD(JSON : self.ad.toJSON())
            
            
            let im = IMG()
            im.image = self.ad.main_image
            self.ad.images.insert(im, at: 0)
            
            if let u = self.ad.main_video, !u.isEmpty, u != "-1"{
                let video = IMG()
                video.image = u
                video.isVideo = true
                self.ad.images.insert(video, at: 0)
            }
            
            
            self.parentBase?.refreshBar()
            self.refreshData()
        }
    }
    
    func refreshData(){
        self.title = ad.title
        Provider.sd_setImage(self.img, urlString: ad.main_image)
        
        
        if User.isRegistered(){
            let same = self.ad.seller_id.intValue == User.getID()
            if same{
                if self.ad.status.intValue != 5{
                    self.adRejectionStack.isHidden = true
                }else{
                    self.adRejectionLbl.text = self.ad.reject_note
                }
                
                self.adStatusLbl.text = self.ad.getStatus().0
                self.adCreatedDatebl.text = self.ad.created_at
                
                if let ea = self.ad.expired_after{
                    if self.ad.status.intValue == 2 && ea.intValue < 0 {
                        self.adStatusLbl.text = "Expired".localized
                    }
                }
                
                self.adExpiresDatebl.text = nil
                
                if let date = self.ad.expiry_date, !date.isEmpty{
                    if let d = Date.init(fromString: date, format: .custom("yyyy-MM-dd hh:mm:ss")){
                        self.adExpiresDatebl.text = "\(d.toString(format: DateFormatType.isoDate))"
                    }
                }
                
                if self.adExpiresDatebl.text == nil{
                    self.adExpiresStack.isHidden = true
                }else{
                    self.adExpiresStack.isHidden = false
                }

            }
        }
        
        var number = ""
        let n = 5 - self.ad.ad_id.stringValue.count
        for _ in 0..<n{
            number += "0"
        }
        number += self.ad.ad_id.stringValue
        self.numberLbl.text = number
        
        self.nameLbl.text = ad.title
        if ad.price.doubleValue == 0{
            self.priceLbl.text = "Free".localized
        }else{
            self.priceLbl.text = ad.price.doubleValue.formatDigital() + "\n " + "S.P".localized
        }
        if ad.publish_date != nil{
            let d = Date.init(fromString: ad.publish_date, format: .custom("yyyy-MM-dd hh:mm:ss"))
            self.dateLbl.text = d?.toString(format: DateFormatType.isoDate)
        }else{
            self.dateLbl.text = nil
        }
        var cat = ""
        cat += (ad.parent_category_name != nil) ? "\(ad.parent_category_name!) --- " : ""
        cat += (ad.category_name != nil) ? "\(ad.category_name!)" : ""
        self.catLbl.text = "\(cat)"
        
//        var loc = ""
//        loc += (ad.city_name != nil) ? "\(ad.city_name!) - " : ""
//        loc += (ad.location_name != nil) ? "\(ad.location_name!)" : ""
        self.cityLbl.text = ad.city_name

//        var loc = ""
//        loc += (ad.city_name != nil) ? "\(ad.city_name!) - " : ""
//        loc += (ad.location_name != nil) ? "\(ad.location_name!)" : ""
        self.locationLbl.text = ad.location_name
        
//        self.sellerLbl.text = ad.seller_name
        self.parentBase?.sellerLbl.text = ad.seller_name
        self.negotiableLbl.text = ad.is_negotiable.Boolean ? "yes".localized : "no".localized
        self.desLbl.text = ad.description
        
        //gallery ad images
        collectionView.delegate = self
        collectionView.dataSource = self
        self.tableView.rowHeight = UITableViewAutomaticDimension
        self.tableView.estimatedRowHeight = 100
        self.tableView.tableFooterView = UIView()  // it's just 1 line, awesome!
        
        //1 Vehicles
        if self.ad.tamplate_id.intValue == 1{
            
            self.manufacture_dateLbl.text = ad.vehicle.manufacture_date
            if ad.vehicle.is_automatic != nil{
                self.is_automaticLbl.text = ad.vehicle.is_automatic.Boolean ? "Automatic".localized : "Manual".localized
            }
            if ad.vehicle.is_new != nil{
                self.is_new1.text = ad.vehicle.is_new.Boolean ? "new".localized : "old".localized
            }
            if self.ad.vehicle.kilometer != nil{
                self.kilometerLbl.text = ad.vehicle.kilometer.doubleValue.formatDigital()
            }
            self.type_nameLbl.text = ad.vehicle.type_name
            self.type_model_nameLbl.text = ad.vehicle.type_model_name
            self.engine_capacityLbl.text = ad.vehicle.engine_capacity
        }
        
        
        //2 Properties
        if self.ad.tamplate_id.intValue == 2{
            // TODO STATE
            self.stateLbl.text = ad.property.property_state_name
            self.rooms_numLbl.text = ad.property.rooms_num
            self.floors_numberLbl.text = ad.property.floors_number
            self.floorLbl.text = ad.property.floor
            if ad.property.with_furniture != nil{
                self.with_furnitureLbl.text = ad.property.with_furniture.Boolean ? "yes".localized : "no".localized
            }
            self.spaceLbl.text = ad.property.space
        }
        
        //3 Mobiles
        if self.ad.tamplate_id.intValue == 3{
            if ad.industry.is_new != nil{
                self.is_new3.text = ad.mobile.is_new.Boolean ? "new".localized : "old".localized
            }
            self.type_nameLbl3.text = ad.mobile.type_name

        }
        
        //4 Electronic
        if self.ad.tamplate_id.intValue == 4{
            if ad.electronic.is_new != nil{
                self.is_new4.text = ad.electronic.is_new.Boolean ? "new".localized : "old".localized
            }
            self.type_nameLbl4.text = ad.electronic.type_name
            self.sizeLbl.text = ad.electronic.size
        }
        
        //5 Fashion
        if self.ad.tamplate_id.intValue == 5{
            if ad.fashion.is_new != nil{
                self.is_new5.text = ad.fashion.is_new.Boolean ? "new".localized : "old".localized
            }
        }
        
        
        //6 Kids
        if self.ad.tamplate_id.intValue == 6,ad.kids.is_new != nil {
            self.is_new2.text = ad.kids.is_new.Boolean ? "new".localized : "old".localized
        }
        
        //7 Fashion
        if self.ad.tamplate_id.intValue == 7{
            if ad.sport.is_new != nil{
                self.is_new7.text = ad.sport.is_new.Boolean ? "new".localized : "old".localized
            }
        }
        
        
        //8 Job
        if self.ad.tamplate_id.intValue == 8{
            self.schedule_nameLbl.text = ad.job.schedule_name
            if ad.job.gender != nil{
                self.genderLbl.text = (ad.job.gender.intValue == 1) ? "Male".localized : "Famale".localized
            }
            self.education_nameLbl.text = ad.job.education_name
            self.certificate_nameLbl.text = ad.job.certificate_name
            self.experienceLbl.text = ad.job.experience
            self.salaryLbl.text = ad.job.salary
            self.vvPrice.isHidden = true
        }
        
        //9 Industry
        if self.ad.tamplate_id.intValue == 9,ad.industry.is_new != nil{
            self.is_new9.text = ad.industry.is_new.Boolean ? "new".localized : "old".localized
        }
        
        
        // ALL DATA
        if self.ad.images.isEmpty{
            self.collectionViewHeight.constant = 40
            self.collectionView.backgroundColor = .clear
        }else{
            self.collectionViewHeight.constant = 110
            self.collectionView.backgroundColor = .white
        }
        
        if User.isRegistered(),self.ad.seller_id.intValue != User.getID() {
            self.favVV.isHidden = false
            let tap = UITapGestureRecognizer.init(target: self, action: #selector(self.changeFav))
            self.imgFav.addGestureRecognizer(tap)
            self.imgFav.isUserInteractionEnabled = true
            if let is_favorite = self.ad.is_favorite{
                self.imgFav.image = (is_favorite.Boolean) ? #imageLiteral(resourceName: "star") :  #imageLiteral(resourceName: "star_copy")
                self.favVV.backgroundColor = (is_favorite.Boolean) ? Theme.Color.White :  Theme.Color.red
                self.favVV.borderColor = Theme.Color.red
                self.favVV.borderWidth = 1

            }else{
                self.favVV.isHidden = true
            }
        }else{
            self.favVV.isHidden = true
        }

        
        self.tableView.reloadData()
        self.collectionView.reloadData()
        self.collectionView2.reloadData()
    }
    
    
    func refreshImageFav(){
        imgFav.transform = CGAffineTransform(scaleX: 0.1, y: 0.1)
        
        UIView.animate(withDuration: 2.0,
                       delay: 0,
                       usingSpringWithDamping: 0.2,
                       initialSpringVelocity: 6.0,
                       options: .allowUserInteraction,
                       animations: { [weak self] in
                        self?.imgFav.transform = .identity
            }, completion: nil)
        
        self.imgFav.image = (self.ad.is_favorite.Boolean) ? #imageLiteral(resourceName: "star") :  #imageLiteral(resourceName: "star_copy")
        self.favVV.backgroundColor = (self.ad.is_favorite.Boolean) ? Theme.Color.White :  Theme.Color.red
        self.favVV.borderColor = Theme.Color.red
        self.favVV.borderWidth = 1
    }
    
    @objc func changeFav(){
        
        if self.ad.is_favorite.Boolean{
            Communication.shared.remove_from_favorite(self.ad.ad_id.intValue, callback: { (res) in
                self.ad.is_favorite = JSON(false)
                self.refreshImageFav()
            })
            
        }else{
            Communication.shared.set_as_favorite(self.ad.ad_id.intValue, callback: { (res) in
                self.ad.is_favorite = JSON(true)
                self.refreshImageFav()
            })
        }
    }
    
    @IBAction func showReportMessages(){
        self.showLoading()
        Communication.shared.get_report_messages { (res) in
            self.hideLoading()
            
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ReportMessagesVC") as! ReportMessagesVC
            
            vc.messages = res
            vc.ad = self.ad
            vc.adDetailsVC = self
            vc.modalPresentationStyle = .overCurrentContext
            vc.modalTransitionStyle = .crossDissolve
            
            self.present(vc, animated: true, completion: nil)
        }
    }
    
    
    
    override func tableView(_ tableView: UITableView, willDisplay cell: UITableViewCell, forRowAt indexPath: IndexPath) {
        cell.selectionStyle = .none
    }
    
    
    override func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        
        if self.ifHidden(index: indexPath){
            return 0
        }
        
        switch indexPath.section {
        case 0:

            if indexPath.row == 0{
                if User.isRegistered(){
                    let same = self.ad.seller_id.intValue == User.getID()
                    if same{
                        return UITableViewAutomaticDimension
                    }
                }
                return 0
            }
            
            let collectionVHeight : CGFloat = (self.ad.images.isEmpty) ? 40 : 110
            return (indexPath.row == 1) ? tableView.frame.height / 2 + collectionVHeight : UITableViewAutomaticDimension
        case 11:
            return UITableViewAutomaticDimension
        default:
            return (self.ad.tamplate_id.intValue == indexPath.section) ? UITableViewAutomaticDimension : 0
        }
    }
    
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return self.ad.images.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "cell", for: indexPath) as! CommericalCell
        
        cell.im = self.ad.images[indexPath.row]
        
        if collectionView.tag != 2{
            if indexPath.row == self.selectedIndex{
                cell.layer.borderColor = Theme.Color.red.cgColor
                cell.layer.borderWidth = 3
            }else{
                cell.layer.borderWidth = 0
            }
            
        }else{
            cell.layer.borderWidth = 0
        }
        
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let h = collectionView.frame.height - 20
        if collectionView.tag == 2{
            return CGSize.init(width: collectionView.frame.width, height: collectionView.frame.height)
        }
        return CGSize.init(width: h, height: h)
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        if collectionView.tag == 2{
            
            var images  = [LightboxImage]()
            
            for i in self.ad.images{
                if let urlString = i.image, let url = URL.init(string: Communication.shared.baseImgsURL + urlString){
                    if !i.isVideo{
                        let im = LightboxImage.init(imageURL: url, text: self.ad.title, videoURL: nil)
                        images.append(im)
                    }else{
                        let im = LightboxImage.init(image: #imageLiteral(resourceName: "ic_play_circle_outline"), text: self.ad.title, videoURL: url)
                        images.append(im)
                    }
                }
            }
            
            
            self.imagesController = LightboxController(images: images)
            
            
            self.imagesController.headerView.deleteButton.isHidden = false
            self.imagesController.headerView.deleteButton.isEnabled = true
            self.imagesController.headerView.deleteButton.setImage(#imageLiteral(resourceName: "share"), for: .normal)

            self.imagesController.headerView.deleteButton.addTarget(self, action: #selector(shareImage), for: .touchUpInside)
            
            if indexPath.row < images.count{
                self.imagesController.dynamicBackground = true
                present(self.imagesController, animated: true, completion: nil)
                self.imagesController.goTo(indexPath.item)
            }
            
        }else {
            self.collectionView2.scrollToItem(at: indexPath, at: .centeredHorizontally, animated: true)
            self.selectedIndex = indexPath.row
            self.collectionView.reloadSections(IndexSet.init(integer: IndexSet.Element.init(0)))
            //            self.collectionView.reloadData()
        }
        //        Provider.sd_setImage(self.img, urlString: self.ad.images[indexPath.row].image)
    }
    
    @objc func shareImage(){
        
        if !self.ad.images[self.imagesController.currentPage].isVideo{
        let imgV = UIImageView()
        
        Provider.sd_setImage(imgV, urlString: self.ad.images[self.imagesController.currentPage].image)
        
        guard let imgg = imgV.image else {
            return
        }
        
            let imageToShare = [ imgg ]
            let activityViewController = UIActivityViewController(activityItems: imageToShare, applicationActivities: nil)
            activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash
            
            activityViewController.excludedActivityTypes = [  ]
        //UIActivityType.airDrop, UIActivityType.postToFacebook
            
            self.imagesController.present(activityViewController, animated: true, completion: nil)
        }else{
            
            let urlString = Communication.shared.baseImgsURL +  self.ad.images[self.imagesController.currentPage].image
//            if let urlToShare = URL.init(string: urlString){
            

                DispatchQueue.global(qos: .background).async {
                    if let url = URL(string: urlString),
                        let urlData = NSData(contentsOf: url) {
                        let documentsPath = NSSearchPathForDirectoriesInDomains(.documentDirectory, .userDomainMask, true)[0];
                        let filePath="\(documentsPath)/dealat.mp4"
                        DispatchQueue.main.async {
                            urlData.write(toFile: filePath, atomically: true)
                            
                            // share video
                            let activityViewController = UIActivityViewController(activityItems: [URL(fileURLWithPath: filePath)], applicationActivities: nil)
                            activityViewController.popoverPresentationController?.sourceView = self.view // so that iPads won't crash
                            activityViewController.excludedActivityTypes = [  ]
                            //UIActivityType.airDrop, UIActivityType.postToFacebook
                            
                            self.imagesController.present(activityViewController, animated: true, completion: nil)
                            
                            
                            // sace video
                           /* PHPhotoLibrary.shared().performChanges({
                                PHAssetChangeRequest.creationRequestForAssetFromVideo(atFileURL: URL(fileURLWithPath: filePath))
                            }) { completed, error in
                                if completed {
                                    print("Video is saved!")
                                }
                            }*/
                            
                        }
                    }
                }
        }
    }
    
    
    //Delegate With ScrollView (scroll item photos)
    override func scrollViewDidScroll(_ scrollView: UIScrollView) {
        if scrollView == self.collectionView2{
            if self.ad.images.count > 0 {
                if self.collectionView2.visibleCells.count > 0{
                    let pp = round(self.collectionView2.contentOffset.x / self.collectionView2.frame.size.width)
                    self.selectedIndex = Int(pp)
                    self.collectionView.reloadData()
                }
            }
        }
    }
    
    
    override func tableView(_ tableView: UITableView, titleForHeaderInSection section: Int) -> String? {
        return nil
    }
    
    
}

